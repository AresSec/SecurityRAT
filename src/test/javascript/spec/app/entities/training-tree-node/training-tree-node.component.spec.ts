import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingTreeNodeComponent } from 'app/entities/training-tree-node/training-tree-node.component';
import { TrainingTreeNodeService } from 'app/entities/training-tree-node/training-tree-node.service';
import { TrainingTreeNode } from 'app/shared/model/training-tree-node.model';

describe('Component Tests', () => {
  describe('TrainingTreeNode Management Component', () => {
    let comp: TrainingTreeNodeComponent;
    let fixture: ComponentFixture<TrainingTreeNodeComponent>;
    let service: TrainingTreeNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingTreeNodeComponent]
      })
        .overrideTemplate(TrainingTreeNodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingTreeNodeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingTreeNodeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TrainingTreeNode(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.trainingTreeNodes && comp.trainingTreeNodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
