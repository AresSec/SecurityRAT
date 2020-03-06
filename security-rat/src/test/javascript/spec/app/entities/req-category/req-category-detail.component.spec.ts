import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { ReqCategoryDetailComponent } from 'app/entities/req-category/req-category-detail.component';
import { ReqCategory } from 'app/shared/model/req-category.model';

describe('Component Tests', () => {
  describe('ReqCategory Management Detail Component', () => {
    let comp: ReqCategoryDetailComponent;
    let fixture: ComponentFixture<ReqCategoryDetailComponent>;
    const route = ({ data: of({ reqCategory: new ReqCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [ReqCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReqCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReqCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reqCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reqCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
