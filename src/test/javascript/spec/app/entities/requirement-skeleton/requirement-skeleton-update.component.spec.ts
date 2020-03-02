import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { RequirementSkeletonUpdateComponent } from 'app/entities/requirement-skeleton/requirement-skeleton-update.component';
import { RequirementSkeletonService } from 'app/entities/requirement-skeleton/requirement-skeleton.service';
import { RequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

describe('Component Tests', () => {
  describe('RequirementSkeleton Management Update Component', () => {
    let comp: RequirementSkeletonUpdateComponent;
    let fixture: ComponentFixture<RequirementSkeletonUpdateComponent>;
    let service: RequirementSkeletonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [RequirementSkeletonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RequirementSkeletonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequirementSkeletonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequirementSkeletonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RequirementSkeleton(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RequirementSkeleton();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
