import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { CollectionInstanceComponent } from 'app/entities/collection-instance/collection-instance.component';
import { CollectionInstanceService } from 'app/entities/collection-instance/collection-instance.service';
import { CollectionInstance } from 'app/shared/model/collection-instance.model';

describe('Component Tests', () => {
  describe('CollectionInstance Management Component', () => {
    let comp: CollectionInstanceComponent;
    let fixture: ComponentFixture<CollectionInstanceComponent>;
    let service: CollectionInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [CollectionInstanceComponent]
      })
        .overrideTemplate(CollectionInstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollectionInstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollectionInstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CollectionInstance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.collectionInstances && comp.collectionInstances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
