import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TrainingTreeNodeService } from 'app/entities/training-tree-node/training-tree-node.service';
import { ITrainingTreeNode, TrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeType } from 'app/shared/model/enumerations/training-tree-node-type.model';

describe('Service Tests', () => {
  describe('TrainingTreeNode Service', () => {
    let injector: TestBed;
    let service: TrainingTreeNodeService;
    let httpMock: HttpTestingController;
    let elemDefault: ITrainingTreeNode;
    let expectedResult: ITrainingTreeNode | ITrainingTreeNode[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TrainingTreeNodeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TrainingTreeNode(0, TrainingTreeNodeType.RequirementNode, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TrainingTreeNode', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TrainingTreeNode()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TrainingTreeNode', () => {
        const returnedFromService = Object.assign(
          {
            nodeType: 'BBBBBB',
            sortOrder: 1,
            active: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TrainingTreeNode', () => {
        const returnedFromService = Object.assign(
          {
            nodeType: 'BBBBBB',
            sortOrder: 1,
            active: true
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

      it('should delete a TrainingTreeNode', () => {
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
