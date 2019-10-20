import bodyParser from 'body-parser';
import dotenv from 'dotenv';
import express from 'express';
import { initTracerFromEnv } from 'jaeger-client';
import * as opentracing from 'opentracing';

dotenv.config();

const port = process.env.SERVER_PORT;

const config = {
  reporter: {
    logSpans: false,
  },
  sampler: {
    param: 1,
    type: 'const',
  }
};
const options = {
  logger: {
    error: (msg: string) => {
      // tslint:disable-next-line:no-console
      console.log('ERROR ', msg);
    },
    info: (msg: string) => {
      // tslint:disable-next-line:no-console
      console.log('INFO  ', msg);
    },
  },
};
const tracer = initTracerFromEnv(config, options) as opentracing.Tracer;

export const createControllerSpan = (controller: string, operation: string, headers: any) => {
  let traceSpan: opentracing.Span;
  // NOTE: OpenTracing type definitions at
  // <https://github.com/opentracing/opentracing-javascript/blob/master/src/tracer.ts>
  const parentSpanContext = tracer.extract(opentracing.FORMAT_HTTP_HEADERS, headers);
  if (parentSpanContext) {
    traceSpan = tracer.startSpan(operation, {
      childOf: parentSpanContext,
      tags: {
        [opentracing.Tags.SPAN_KIND]: opentracing.Tags.SPAN_KIND_RPC_SERVER,
        [opentracing.Tags.COMPONENT]: controller
      }
    });
  } else {
    traceSpan = tracer.startSpan(operation, {
      tags: {
        [opentracing.Tags.SPAN_KIND]: opentracing.Tags.SPAN_KIND_RPC_SERVER,
        [opentracing.Tags.COMPONENT]: controller
      }
    });
  }
  return traceSpan;
};

export const finishSpanWithResult = (span: opentracing.Span, status: number, errorTag?: boolean) => {
  span.setTag(opentracing.Tags.HTTP_STATUS_CODE, status);
  if (errorTag) {
    span.setTag(opentracing.Tags.ERROR, true);
  }
  span.finish();
};

const app = express();
app.use(bodyParser.json());

app.post('/documents', (req, res) => {
  const traceSpan = createControllerSpan('documentGenerator', 'generateDocument', req.headers);

  try {
    const document = req.body;
    finishSpanWithResult(traceSpan, 200);
    return res.status(200).contentType('text/asciidoc')
      .send(`
= ${document.title}

${document.content}
`);
  } catch (error) {
    finishSpanWithResult(traceSpan, 500, true);
    // tslint:disable-next-line:no-console
    console.log('error while listing things ', error);
    return res.status(500).send();
  }
});

app.listen(port, () => {
  // tslint:disable-next-line:no-console
  console.log(`server started at http://localhost:${port}`);
});
