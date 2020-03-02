import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { StatusColumnValueUpdateComponent } from 'app/entities/status-column-value/status-column-value-update.component';
import { StatusColumnValueService } from 'app/entities/status-column-value/status-column-value.service';
import { StatusColumnValue } from 'app/shared/model/status-column-value.model';

describe('Component Tests', () => {
  describe('StatusColumnValue Management Update Component', () => {
    let comp: StatusColumnValueUpdateComponent;
    let fixture: ComponentFixture<StatusColumnValueUpdateComponent>;
    let service: StatusColumnValueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [StatusColumnValueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StatusColumnValueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatusColumnValueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatusColumnValueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StatusColumnValue(123);
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
        const entity = new StatusColumnValue();
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
