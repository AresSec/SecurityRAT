import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingRequirementNodeUpdateComponent } from 'app/entities/training-requirement-node/training-requirement-node-update.component';
import { TrainingRequirementNodeService } from 'app/entities/training-requirement-node/training-requirement-node.service';
import { TrainingRequirementNode } from 'app/shared/model/training-requirement-node.model';

describe('Component Tests', () => {
  describe('TrainingRequirementNode Management Update Component', () => {
    let comp: TrainingRequirementNodeUpdateComponent;
    let fixture: ComponentFixture<TrainingRequirementNodeUpdateComponent>;
    let service: TrainingRequirementNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingRequirementNodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrainingRequirementNodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingRequirementNodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingRequirementNodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TrainingRequirementNode(123);
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
        const entity = new TrainingRequirementNode();
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
