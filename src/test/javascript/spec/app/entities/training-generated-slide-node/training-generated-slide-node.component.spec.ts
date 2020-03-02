import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingGeneratedSlideNodeComponent } from 'app/entities/training-generated-slide-node/training-generated-slide-node.component';
import { TrainingGeneratedSlideNodeService } from 'app/entities/training-generated-slide-node/training-generated-slide-node.service';
import { TrainingGeneratedSlideNode } from 'app/shared/model/training-generated-slide-node.model';

describe('Component Tests', () => {
  describe('TrainingGeneratedSlideNode Management Component', () => {
    let comp: TrainingGeneratedSlideNodeComponent;
    let fixture: ComponentFixture<TrainingGeneratedSlideNodeComponent>;
    let service: TrainingGeneratedSlideNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingGeneratedSlideNodeComponent]
      })
        .overrideTemplate(TrainingGeneratedSlideNodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingGeneratedSlideNodeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingGeneratedSlideNodeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TrainingGeneratedSlideNode(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.trainingGeneratedSlideNodes && comp.trainingGeneratedSlideNodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
