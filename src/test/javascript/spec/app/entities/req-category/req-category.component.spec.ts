import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { ReqCategoryComponent } from 'app/entities/req-category/req-category.component';
import { ReqCategoryService } from 'app/entities/req-category/req-category.service';
import { ReqCategory } from 'app/shared/model/req-category.model';

describe('Component Tests', () => {
  describe('ReqCategory Management Component', () => {
    let comp: ReqCategoryComponent;
    let fixture: ComponentFixture<ReqCategoryComponent>;
    let service: ReqCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [ReqCategoryComponent]
      })
        .overrideTemplate(ReqCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReqCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReqCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReqCategory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reqCategories && comp.reqCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
