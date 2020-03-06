import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TagCategoryDetailComponent } from 'app/entities/tag-category/tag-category-detail.component';
import { TagCategory } from 'app/shared/model/tag-category.model';

describe('Component Tests', () => {
  describe('TagCategory Management Detail Component', () => {
    let comp: TagCategoryDetailComponent;
    let fixture: ComponentFixture<TagCategoryDetailComponent>;
    const route = ({ data: of({ tagCategory: new TagCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TagCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TagCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tagCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tagCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
