import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { RequirementSkeletonComponent } from 'app/entities/requirement-skeleton/requirement-skeleton.component';
import { RequirementSkeletonService } from 'app/entities/requirement-skeleton/requirement-skeleton.service';
import { RequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

describe('Component Tests', () => {
  describe('RequirementSkeleton Management Component', () => {
    let comp: RequirementSkeletonComponent;
    let fixture: ComponentFixture<RequirementSkeletonComponent>;
    let service: RequirementSkeletonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [RequirementSkeletonComponent]
      })
        .overrideTemplate(RequirementSkeletonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequirementSkeletonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequirementSkeletonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RequirementSkeleton(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.requirementSkeletons && comp.requirementSkeletons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
