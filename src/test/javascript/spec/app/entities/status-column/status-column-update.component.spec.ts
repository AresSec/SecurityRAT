import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { StatusColumnUpdateComponent } from 'app/entities/status-column/status-column-update.component';
import { StatusColumnService } from 'app/entities/status-column/status-column.service';
import { StatusColumn } from 'app/shared/model/status-column.model';

describe('Component Tests', () => {
  describe('StatusColumn Management Update Component', () => {
    let comp: StatusColumnUpdateComponent;
    let fixture: ComponentFixture<StatusColumnUpdateComponent>;
    let service: StatusColumnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [StatusColumnUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StatusColumnUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatusColumnUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatusColumnService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StatusColumn(123);
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
        const entity = new StatusColumn();
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
