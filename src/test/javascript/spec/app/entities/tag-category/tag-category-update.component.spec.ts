import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TagCategoryUpdateComponent } from 'app/entities/tag-category/tag-category-update.component';
import { TagCategoryService } from 'app/entities/tag-category/tag-category.service';
import { TagCategory } from 'app/shared/model/tag-category.model';

describe('Component Tests', () => {
  describe('TagCategory Management Update Component', () => {
    let comp: TagCategoryUpdateComponent;
    let fixture: ComponentFixture<TagCategoryUpdateComponent>;
    let service: TagCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TagCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TagCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TagCategory(123);
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
        const entity = new TagCategory();
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
