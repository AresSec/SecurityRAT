import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { StatusColumnComponent } from 'app/entities/status-column/status-column.component';
import { StatusColumnService } from 'app/entities/status-column/status-column.service';
import { StatusColumn } from 'app/shared/model/status-column.model';

describe('Component Tests', () => {
  describe('StatusColumn Management Component', () => {
    let comp: StatusColumnComponent;
    let fixture: ComponentFixture<StatusColumnComponent>;
    let service: StatusColumnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [StatusColumnComponent]
      })
        .overrideTemplate(StatusColumnComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatusColumnComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatusColumnService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StatusColumn(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.statusColumns && comp.statusColumns[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
