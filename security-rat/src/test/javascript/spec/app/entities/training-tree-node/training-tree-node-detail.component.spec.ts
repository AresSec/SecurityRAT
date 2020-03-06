import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingTreeNodeDetailComponent } from 'app/entities/training-tree-node/training-tree-node-detail.component';
import { TrainingTreeNode } from 'app/shared/model/training-tree-node.model';

describe('Component Tests', () => {
  describe('TrainingTreeNode Management Detail Component', () => {
    let comp: TrainingTreeNodeDetailComponent;
    let fixture: ComponentFixture<TrainingTreeNodeDetailComponent>;
    const route = ({ data: of({ trainingTreeNode: new TrainingTreeNode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingTreeNodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrainingTreeNodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainingTreeNodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load trainingTreeNode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trainingTreeNode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
