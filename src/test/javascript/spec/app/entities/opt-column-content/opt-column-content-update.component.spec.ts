import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { OptColumnContentUpdateComponent } from 'app/entities/opt-column-content/opt-column-content-update.component';
import { OptColumnContentService } from 'app/entities/opt-column-content/opt-column-content.service';
import { OptColumnContent } from 'app/shared/model/opt-column-content.model';

describe('Component Tests', () => {
  describe('OptColumnContent Management Update Component', () => {
    let comp: OptColumnContentUpdateComponent;
    let fixture: ComponentFixture<OptColumnContentUpdateComponent>;
    let service: OptColumnContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [OptColumnContentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OptColumnContentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptColumnContentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptColumnContentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OptColumnContent(123);
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
        const entity = new OptColumnContent();
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
