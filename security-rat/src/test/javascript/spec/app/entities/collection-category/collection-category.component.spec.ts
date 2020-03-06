import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { CollectionCategoryComponent } from 'app/entities/collection-category/collection-category.component';
import { CollectionCategoryService } from 'app/entities/collection-category/collection-category.service';
import { CollectionCategory } from 'app/shared/model/collection-category.model';

describe('Component Tests', () => {
  describe('CollectionCategory Management Component', () => {
    let comp: CollectionCategoryComponent;
    let fixture: ComponentFixture<CollectionCategoryComponent>;
    let service: CollectionCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [CollectionCategoryComponent]
      })
        .overrideTemplate(CollectionCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollectionCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollectionCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CollectionCategory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.collectionCategories && comp.collectionCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
