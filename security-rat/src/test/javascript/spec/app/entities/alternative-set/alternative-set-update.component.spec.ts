import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { AlternativeSetUpdateComponent } from 'app/entities/alternative-set/alternative-set-update.component';
import { AlternativeSetService } from 'app/entities/alternative-set/alternative-set.service';
import { AlternativeSet } from 'app/shared/model/alternative-set.model';

describe('Component Tests', () => {
  describe('AlternativeSet Management Update Component', () => {
    let comp: AlternativeSetUpdateComponent;
    let fixture: ComponentFixture<AlternativeSetUpdateComponent>;
    let service: AlternativeSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [AlternativeSetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlternativeSetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlternativeSetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlternativeSetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlternativeSet(123);
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
        const entity = new AlternativeSet();
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
