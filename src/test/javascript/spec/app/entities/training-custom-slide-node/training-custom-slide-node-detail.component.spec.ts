import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingCustomSlideNodeDetailComponent } from 'app/entities/training-custom-slide-node/training-custom-slide-node-detail.component';
import { TrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';

describe('Component Tests', () => {
  describe('TrainingCustomSlideNode Management Detail Component', () => {
    let comp: TrainingCustomSlideNodeDetailComponent;
    let fixture: ComponentFixture<TrainingCustomSlideNodeDetailComponent>;
    const route = ({ data: of({ trainingCustomSlideNode: new TrainingCustomSlideNode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingCustomSlideNodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrainingCustomSlideNodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainingCustomSlideNodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load trainingCustomSlideNode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trainingCustomSlideNode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
