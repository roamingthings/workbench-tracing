import { initTracerFromEnv } from 'jaeger-client';
import * as opentracing from 'opentracing';

const config = {
  reporter: {
    logSpans: false
  },
  sampler: {
    param: 1,
    type: 'const'
  }
};

const options = {
  logger: {
    error: (msg: string): void => {
      // eslint-disable-next-line no-console
      console.log('ERROR ', msg);
    },
    info: (msg: string): void => {
      // eslint-disable-next-line no-console
      console.log('INFO  ', msg);
    }
  }
};

const tracer = initTracerFromEnv(config, options) as opentracing.Tracer;

const createControllerSpan = (controller: string, operation: string, headers: object): opentracing.Span => {
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

export const finishSpanWithResult = (span: opentracing.Span, status: number, errorTag?: boolean): void => {
  span.setTag(opentracing.Tags.HTTP_STATUS_CODE, status);
  if (errorTag) {
    span.setTag(opentracing.Tags.ERROR, true);
  }
  span.finish();
};

export default {
  createControllerSpan,
  finishSpanWithResult,
  tracer
};
