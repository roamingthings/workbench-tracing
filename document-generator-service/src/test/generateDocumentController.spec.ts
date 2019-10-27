import chai from 'chai';
import chaiHttp from 'chai-http';
import { fail } from 'assert';
import app from '../server';

// Configure chai
chai.use(chaiHttp);
chai.should();

describe('Generate Document', () => {
  describe('POST /documents', () => {
    it('should generate a document', (done) => {
      chai.request(app)
        .post('/documents')
        .set('Accept', 'text/asciidoc')
        .set('Content-Type', 'application/json')
        .send({
          content: 'The content\n\nin the box',
          title: 'The title'
        })
        .end((err, res) => {
          if (err) {
            fail();
          } else {
            res.should.have.status(200);
            res.should.have.header('Content-Type', /text\/asciidoc/);
            res.text.should.be.eql(
              `
= The title

The content

in the box
`
            );
            done();
          }
        });
    });
  });
})
;
