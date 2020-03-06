import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { CollectionCategoryUpdateComponent } from 'app/entities/collection-category/collection-category-update.component';
import { CollectionCategoryService } from 'app/entities/collection-category/collection-category.service';
import { CollectionCategory } from 'app/shared/model/collection-category.model';

describe('Component Tests', () => {
  describe('CollectionCategory Management Update Component', () => {
    let comp: CollectionCategoryUpdateComponent;
    let fixture: ComponentFixture<CollectionCategoryUpdateComponent>;
    let service: CollectionCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [CollectionCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CollectionCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollectionCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollectionCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CollectionCategory(123);
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
        const entity = new CollectionCategory();
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
