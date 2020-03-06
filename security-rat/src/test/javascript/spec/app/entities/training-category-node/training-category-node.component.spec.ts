import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { TrainingCategoryNodeComponent } from 'app/entities/training-category-node/training-category-node.component';
import { TrainingCategoryNodeService } from 'app/entities/training-category-node/training-category-node.service';
import { TrainingCategoryNode } from 'app/shared/model/training-category-node.model';

describe('Component Tests', () => {
  describe('TrainingCategoryNode Management Component', () => {
    let comp: TrainingCategoryNodeComponent;
    let fixture: ComponentFixture<TrainingCategoryNodeComponent>;
    let service: TrainingCategoryNodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TrainingCategoryNodeComponent]
      })
        .overrideTemplate(TrainingCategoryNodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingCategoryNodeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingCategoryNodeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TrainingCategoryNode(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.trainingCategoryNodes && comp.trainingCategoryNodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
