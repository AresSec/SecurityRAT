import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { TagInstanceComponent } from 'app/entities/tag-instance/tag-instance.component';
import { TagInstanceService } from 'app/entities/tag-instance/tag-instance.service';
import { TagInstance } from 'app/shared/model/tag-instance.model';

describe('Component Tests', () => {
  describe('TagInstance Management Component', () => {
    let comp: TagInstanceComponent;
    let fixture: ComponentFixture<TagInstanceComponent>;
    let service: TagInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TagInstanceComponent]
      })
        .overrideTemplate(TagInstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagInstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagInstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TagInstance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tagInstances && comp.tagInstances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
