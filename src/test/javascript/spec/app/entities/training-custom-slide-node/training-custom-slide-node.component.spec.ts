import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingCustomSlideNodeComponent } from 'app/entities/training-custom-slide-node/training-custom-slide-node.component';
import { TrainingCustomSlideNodeService } from 'app/entities/training-custom-slide-node/training-custom-slide-node.service';
import { TrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';

describe('Component Tests', () => {
  describe('TrainingCustomSlideNode Management Component', () => {
    let comp: TrainingCustomSlideNodeComponent;
    let fixture: ComponentFixture<TrainingCustomSlideNodeComponent>;
    let service: TrainingCustomSlideNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingCustomSlideNodeComponent]
      })
        .overrideTemplate(TrainingCustomSlideNodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingCustomSlideNodeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingCustomSlideNodeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TrainingCustomSlideNode(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.trainingCustomSlideNodes && comp.trainingCustomSlideNodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
