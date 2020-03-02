import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { SlideTemplateComponent } from 'app/entities/slide-template/slide-template.component';
import { SlideTemplateService } from 'app/entities/slide-template/slide-template.service';
import { SlideTemplate } from 'app/shared/model/slide-template.model';

describe('Component Tests', () => {
  describe('SlideTemplate Management Component', () => {
    let comp: SlideTemplateComponent;
    let fixture: ComponentFixture<SlideTemplateComponent>;
    let service: SlideTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [SlideTemplateComponent]
      })
        .overrideTemplate(SlideTemplateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SlideTemplateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SlideTemplateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SlideTemplate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.slideTemplates && comp.slideTemplates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
