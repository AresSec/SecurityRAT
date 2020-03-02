import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingBranchNodeComponent } from 'app/entities/training-branch-node/training-branch-node.component';
import { TrainingBranchNodeService } from 'app/entities/training-branch-node/training-branch-node.service';
import { TrainingBranchNode } from 'app/shared/model/training-branch-node.model';

describe('Component Tests', () => {
  describe('TrainingBranchNode Management Component', () => {
    let comp: TrainingBranchNodeComponent;
    let fixture: ComponentFixture<TrainingBranchNodeComponent>;
    let service: TrainingBranchNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingBranchNodeComponent]
      })
        .overrideTemplate(TrainingBranchNodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingBranchNodeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingBranchNodeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TrainingBranchNode(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.trainingBranchNodes && comp.trainingBranchNodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
