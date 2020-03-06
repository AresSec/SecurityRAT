import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingCategoryNodeUpdateComponent } from 'app/entities/training-category-node/training-category-node-update.component';
import { TrainingCategoryNodeService } from 'app/entities/training-category-node/training-category-node.service';
import { TrainingCategoryNode } from 'app/shared/model/training-category-node.model';

describe('Component Tests', () => {
  describe('TrainingCategoryNode Management Update Component', () => {
    let comp: TrainingCategoryNodeUpdateComponent;
    let fixture: ComponentFixture<TrainingCategoryNodeUpdateComponent>;
    let service: TrainingCategoryNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingCategoryNodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrainingCategoryNodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingCategoryNodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingCategoryNodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TrainingCategoryNode(123);
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
        const entity = new TrainingCategoryNode();
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
