import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { CollectionInstanceUpdateComponent } from 'app/entities/collection-instance/collection-instance-update.component';
import { CollectionInstanceService } from 'app/entities/collection-instance/collection-instance.service';
import { CollectionInstance } from 'app/shared/model/collection-instance.model';

describe('Component Tests', () => {
  describe('CollectionInstance Management Update Component', () => {
    let comp: CollectionInstanceUpdateComponent;
    let fixture: ComponentFixture<CollectionInstanceUpdateComponent>;
    let service: CollectionInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [CollectionInstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CollectionInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollectionInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollectionInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CollectionInstance(123);
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
        const entity = new CollectionInstance();
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
