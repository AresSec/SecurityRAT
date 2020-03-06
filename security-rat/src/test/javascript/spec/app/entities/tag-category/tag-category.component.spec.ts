import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { TagCategoryComponent } from 'app/entities/tag-category/tag-category.component';
import { TagCategoryService } from 'app/entities/tag-category/tag-category.service';
import { TagCategory } from 'app/shared/model/tag-category.model';

describe('Component Tests', () => {
  describe('TagCategory Management Component', () => {
    let comp: TagCategoryComponent;
    let fixture: ComponentFixture<TagCategoryComponent>;
    let service: TagCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TagCategoryComponent]
      })
        .overrideTemplate(TagCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TagCategory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tagCategories && comp.tagCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
