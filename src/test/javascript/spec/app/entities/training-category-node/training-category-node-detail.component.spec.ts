import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingCategoryNodeDetailComponent } from 'app/entities/training-category-node/training-category-node-detail.component';
import { TrainingCategoryNode } from 'app/shared/model/training-category-node.model';

describe('Component Tests', () => {
  describe('TrainingCategoryNode Management Detail Component', () => {
    let comp: TrainingCategoryNodeDetailComponent;
    let fixture: ComponentFixture<TrainingCategoryNodeDetailComponent>;
    const route = ({ data: of({ trainingCategoryNode: new TrainingCategoryNode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingCategoryNodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrainingCategoryNodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainingCategoryNodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load trainingCategoryNode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trainingCategoryNode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
