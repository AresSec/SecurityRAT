import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { OptColumnTypeUpdateComponent } from 'app/entities/opt-column-type/opt-column-type-update.component';
import { OptColumnTypeService } from 'app/entities/opt-column-type/opt-column-type.service';
import { OptColumnType } from 'app/shared/model/opt-column-type.model';

describe('Component Tests', () => {
  describe('OptColumnType Management Update Component', () => {
    let comp: OptColumnTypeUpdateComponent;
    let fixture: ComponentFixture<OptColumnTypeUpdateComponent>;
    let service: OptColumnTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [OptColumnTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OptColumnTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptColumnTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptColumnTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OptColumnType(123);
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
        const entity = new OptColumnType();
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
