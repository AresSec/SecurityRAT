import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { ReqCategoryUpdateComponent } from 'app/entities/req-category/req-category-update.component';
import { ReqCategoryService } from 'app/entities/req-category/req-category.service';
import { ReqCategory } from 'app/shared/model/req-category.model';

describe('Component Tests', () => {
  describe('ReqCategory Management Update Component', () => {
    let comp: ReqCategoryUpdateComponent;
    let fixture: ComponentFixture<ReqCategoryUpdateComponent>;
    let service: ReqCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [ReqCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReqCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReqCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReqCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReqCategory(123);
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
        const entity = new ReqCategory();
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
