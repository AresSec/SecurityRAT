import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { CollectionCategoryDetailComponent } from 'app/entities/collection-category/collection-category-detail.component';
import { CollectionCategory } from 'app/shared/model/collection-category.model';

describe('Component Tests', () => {
  describe('CollectionCategory Management Detail Component', () => {
    let comp: CollectionCategoryDetailComponent;
    let fixture: ComponentFixture<CollectionCategoryDetailComponent>;
    const route = ({ data: of({ collectionCategory: new CollectionCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [CollectionCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CollectionCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollectionCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load collectionCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.collectionCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
