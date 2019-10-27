/* eslint-disable import/first */
import dotenv from 'dotenv';
dotenv.config();
import express from 'express';
import GenerateDocumentController from './generateDocumentController';

const port = process.env.SERVER_PORT;

const app = express();
app.use(express.json());

app.post('/documents', GenerateDocumentController.generateDocument);

app.listen(port, () => {
  // eslint-disable-next-line no-console
  console.log(`server started at http://localhost:${port}`);
});

export default app;
