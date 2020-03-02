import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RequirementSkeletonService } from 'app/entities/requirement-skeleton/requirement-skeleton.service';
import { IRequirementSkeleton, RequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

describe('Service Tests', () => {
  describe('RequirementSkeleton Service', () => {
    let injector: TestBed;
    let service: RequirementSkeletonService;
    let httpMock: HttpTestingController;
    let elemDefault: IRequirementSkeleton;
    let expectedResult: IRequirementSkeleton | IRequirementSkeleton[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RequirementSkeletonService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new RequirementSkeleton(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RequirementSkeleton', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new RequirementSkeleton()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RequirementSkeleton', () => {
        const returnedFromService = Object.assign(
          {
            universalId: 'BBBBBB',
            shortName: 'BBBBBB',
            description: 'BBBBBB',
            showOrder: 1,
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

      it('should return a list of RequirementSkeleton', () => {
        const returnedFromService = Object.assign(
          {
            universalId: 'BBBBBB',
            shortName: 'BBBBBB',
            description: 'BBBBBB',
            showOrder: 1,
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

      it('should delete a RequirementSkeleton', () => {
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
