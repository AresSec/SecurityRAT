import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { RequirementSkeletonDetailComponent } from 'app/entities/requirement-skeleton/requirement-skeleton-detail.component';
import { RequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

describe('Component Tests', () => {
  describe('RequirementSkeleton Management Detail Component', () => {
    let comp: RequirementSkeletonDetailComponent;
    let fixture: ComponentFixture<RequirementSkeletonDetailComponent>;
    const route = ({ data: of({ requirementSkeleton: new RequirementSkeleton(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [RequirementSkeletonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RequirementSkeletonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequirementSkeletonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load requirementSkeleton on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.requirementSkeleton).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
