import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingGeneratedSlideNodeDetailComponent } from 'app/entities/training-generated-slide-node/training-generated-slide-node-detail.component';
import { TrainingGeneratedSlideNode } from 'app/shared/model/training-generated-slide-node.model';

describe('Component Tests', () => {
  describe('TrainingGeneratedSlideNode Management Detail Component', () => {
    let comp: TrainingGeneratedSlideNodeDetailComponent;
    let fixture: ComponentFixture<TrainingGeneratedSlideNodeDetailComponent>;
    const route = ({ data: of({ trainingGeneratedSlideNode: new TrainingGeneratedSlideNode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingGeneratedSlideNodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrainingGeneratedSlideNodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainingGeneratedSlideNodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load trainingGeneratedSlideNode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trainingGeneratedSlideNode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
