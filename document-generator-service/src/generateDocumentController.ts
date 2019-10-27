import { Request, Response } from 'express';
import tracing from './tracing';

class GenerateDocumentController {
  public static generateDocument (req: Request, res: Response): Response {
    const traceSpan = tracing.createControllerSpan('documentGenerator', 'generateDocument', req.headers);

    try {
      const document = req.body;
      tracing.finishSpanWithResult(traceSpan, 200);
      return res.status(200).contentType('text/asciidoc')
        .send(`
= ${document.title}

${document.content}
`);
    } catch (error) {
      tracing.finishSpanWithResult(traceSpan, 500, true);
      // eslint-disable-next-line no-console
      console.log('error while listing things ', error);
      return res.status(500).send();
    }
  }
}

export default GenerateDocumentController;
