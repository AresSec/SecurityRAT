import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { AlternativeInstanceUpdateComponent } from 'app/entities/alternative-instance/alternative-instance-update.component';
import { AlternativeInstanceService } from 'app/entities/alternative-instance/alternative-instance.service';
import { AlternativeInstance } from 'app/shared/model/alternative-instance.model';

describe('Component Tests', () => {
  describe('AlternativeInstance Management Update Component', () => {
    let comp: AlternativeInstanceUpdateComponent;
    let fixture: ComponentFixture<AlternativeInstanceUpdateComponent>;
    let service: AlternativeInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [AlternativeInstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlternativeInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlternativeInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlternativeInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlternativeInstance(123);
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
        const entity = new AlternativeInstance();
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
