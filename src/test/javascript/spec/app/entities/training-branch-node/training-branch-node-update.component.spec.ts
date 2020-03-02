import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingBranchNodeUpdateComponent } from 'app/entities/training-branch-node/training-branch-node-update.component';
import { TrainingBranchNodeService } from 'app/entities/training-branch-node/training-branch-node.service';
import { TrainingBranchNode } from 'app/shared/model/training-branch-node.model';

describe('Component Tests', () => {
  describe('TrainingBranchNode Management Update Component', () => {
    let comp: TrainingBranchNodeUpdateComponent;
    let fixture: ComponentFixture<TrainingBranchNodeUpdateComponent>;
    let service: TrainingBranchNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingBranchNodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrainingBranchNodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingBranchNodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingBranchNodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TrainingBranchNode(123);
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
        const entity = new TrainingBranchNode();
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
