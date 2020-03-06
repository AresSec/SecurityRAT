import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingTreeNodeUpdateComponent } from 'app/entities/training-tree-node/training-tree-node-update.component';
import { TrainingTreeNodeService } from 'app/entities/training-tree-node/training-tree-node.service';
import { TrainingTreeNode } from 'app/shared/model/training-tree-node.model';

describe('Component Tests', () => {
  describe('TrainingTreeNode Management Update Component', () => {
    let comp: TrainingTreeNodeUpdateComponent;
    let fixture: ComponentFixture<TrainingTreeNodeUpdateComponent>;
    let service: TrainingTreeNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingTreeNodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrainingTreeNodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingTreeNodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingTreeNodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TrainingTreeNode(123);
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
        const entity = new TrainingTreeNode();
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
