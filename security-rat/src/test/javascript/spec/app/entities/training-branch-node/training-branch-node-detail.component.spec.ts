import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingBranchNodeDetailComponent } from 'app/entities/training-branch-node/training-branch-node-detail.component';
import { TrainingBranchNode } from 'app/shared/model/training-branch-node.model';

describe('Component Tests', () => {
  describe('TrainingBranchNode Management Detail Component', () => {
    let comp: TrainingBranchNodeDetailComponent;
    let fixture: ComponentFixture<TrainingBranchNodeDetailComponent>;
    const route = ({ data: of({ trainingBranchNode: new TrainingBranchNode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingBranchNodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrainingBranchNodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainingBranchNodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load trainingBranchNode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trainingBranchNode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
