import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { OptColumnContentComponent } from 'app/entities/opt-column-content/opt-column-content.component';
import { OptColumnContentService } from 'app/entities/opt-column-content/opt-column-content.service';
import { OptColumnContent } from 'app/shared/model/opt-column-content.model';

describe('Component Tests', () => {
  describe('OptColumnContent Management Component', () => {
    let comp: OptColumnContentComponent;
    let fixture: ComponentFixture<OptColumnContentComponent>;
    let service: OptColumnContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [OptColumnContentComponent]
      })
        .overrideTemplate(OptColumnContentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptColumnContentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptColumnContentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OptColumnContent(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.optColumnContents && comp.optColumnContents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
