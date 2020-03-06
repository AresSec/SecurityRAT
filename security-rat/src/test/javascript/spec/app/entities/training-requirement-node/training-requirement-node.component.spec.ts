import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingRequirementNodeComponent } from 'app/entities/training-requirement-node/training-requirement-node.component';
import { TrainingRequirementNodeService } from 'app/entities/training-requirement-node/training-requirement-node.service';
import { TrainingRequirementNode } from 'app/shared/model/training-requirement-node.model';

describe('Component Tests', () => {
  describe('TrainingRequirementNode Management Component', () => {
    let comp: TrainingRequirementNodeComponent;
    let fixture: ComponentFixture<TrainingRequirementNodeComponent>;
    let service: TrainingRequirementNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingRequirementNodeComponent]
      })
        .overrideTemplate(TrainingRequirementNodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingRequirementNodeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingRequirementNodeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TrainingRequirementNode(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.trainingRequirementNodes && comp.trainingRequirementNodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
