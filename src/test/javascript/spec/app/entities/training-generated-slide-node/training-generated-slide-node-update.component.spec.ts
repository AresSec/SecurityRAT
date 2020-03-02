import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingGeneratedSlideNodeUpdateComponent } from 'app/entities/training-generated-slide-node/training-generated-slide-node-update.component';
import { TrainingGeneratedSlideNodeService } from 'app/entities/training-generated-slide-node/training-generated-slide-node.service';
import { TrainingGeneratedSlideNode } from 'app/shared/model/training-generated-slide-node.model';

describe('Component Tests', () => {
  describe('TrainingGeneratedSlideNode Management Update Component', () => {
    let comp: TrainingGeneratedSlideNodeUpdateComponent;
    let fixture: ComponentFixture<TrainingGeneratedSlideNodeUpdateComponent>;
    let service: TrainingGeneratedSlideNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingGeneratedSlideNodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrainingGeneratedSlideNodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingGeneratedSlideNodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingGeneratedSlideNodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TrainingGeneratedSlideNode(123);
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
        const entity = new TrainingGeneratedSlideNode();
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
