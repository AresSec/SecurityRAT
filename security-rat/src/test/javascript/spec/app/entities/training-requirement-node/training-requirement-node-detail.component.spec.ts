import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingRequirementNodeDetailComponent } from 'app/entities/training-requirement-node/training-requirement-node-detail.component';
import { TrainingRequirementNode } from 'app/shared/model/training-requirement-node.model';

describe('Component Tests', () => {
  describe('TrainingRequirementNode Management Detail Component', () => {
    let comp: TrainingRequirementNodeDetailComponent;
    let fixture: ComponentFixture<TrainingRequirementNodeDetailComponent>;
    const route = ({ data: of({ trainingRequirementNode: new TrainingRequirementNode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingRequirementNodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrainingRequirementNodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainingRequirementNodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load trainingRequirementNode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trainingRequirementNode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
