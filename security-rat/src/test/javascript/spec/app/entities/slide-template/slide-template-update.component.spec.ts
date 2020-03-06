import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { SlideTemplateUpdateComponent } from 'app/entities/slide-template/slide-template-update.component';
import { SlideTemplateService } from 'app/entities/slide-template/slide-template.service';
import { SlideTemplate } from 'app/shared/model/slide-template.model';

describe('Component Tests', () => {
  describe('SlideTemplate Management Update Component', () => {
    let comp: SlideTemplateUpdateComponent;
    let fixture: ComponentFixture<SlideTemplateUpdateComponent>;
    let service: SlideTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [SlideTemplateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SlideTemplateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SlideTemplateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SlideTemplateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SlideTemplate(123);
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
        const entity = new SlideTemplate();
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
