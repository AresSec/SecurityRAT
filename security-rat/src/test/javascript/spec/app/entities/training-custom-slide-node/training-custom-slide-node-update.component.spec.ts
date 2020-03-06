import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingCustomSlideNodeUpdateComponent } from 'app/entities/training-custom-slide-node/training-custom-slide-node-update.component';
import { TrainingCustomSlideNodeService } from 'app/entities/training-custom-slide-node/training-custom-slide-node.service';
import { TrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';

describe('Component Tests', () => {
  describe('TrainingCustomSlideNode Management Update Component', () => {
    let comp: TrainingCustomSlideNodeUpdateComponent;
    let fixture: ComponentFixture<TrainingCustomSlideNodeUpdateComponent>;
    let service: TrainingCustomSlideNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingCustomSlideNodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrainingCustomSlideNodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingCustomSlideNodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingCustomSlideNodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TrainingCustomSlideNode(123);
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
        const entity = new TrainingCustomSlideNode();
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
