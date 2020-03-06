import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TrainingCustomSlideNodeService } from 'app/entities/training-custom-slide-node/training-custom-slide-node.service';
import { ITrainingCustomSlideNode, TrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';

describe('Service Tests', () => {
  describe('TrainingCustomSlideNode Service', () => {
    let injector: TestBed;
    let service: TrainingCustomSlideNodeService;
    let httpMock: HttpTestingController;
    let elemDefault: ITrainingCustomSlideNode;
    let expectedResult: ITrainingCustomSlideNode | ITrainingCustomSlideNode[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TrainingCustomSlideNodeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TrainingCustomSlideNode(0, 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TrainingCustomSlideNode', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TrainingCustomSlideNode()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TrainingCustomSlideNode', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            content: 'BBBBBB',
            anchor: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TrainingCustomSlideNode', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            content: 'BBBBBB',
            anchor: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TrainingCustomSlideNode', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
