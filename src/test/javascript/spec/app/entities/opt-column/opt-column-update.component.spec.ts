import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { OptColumnUpdateComponent } from 'app/entities/opt-column/opt-column-update.component';
import { OptColumnService } from 'app/entities/opt-column/opt-column.service';
import { OptColumn } from 'app/shared/model/opt-column.model';

describe('Component Tests', () => {
  describe('OptColumn Management Update Component', () => {
    let comp: OptColumnUpdateComponent;
    let fixture: ComponentFixture<OptColumnUpdateComponent>;
    let service: OptColumnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [OptColumnUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OptColumnUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptColumnUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptColumnService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OptColumn(123);
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
        const entity = new OptColumn();
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
