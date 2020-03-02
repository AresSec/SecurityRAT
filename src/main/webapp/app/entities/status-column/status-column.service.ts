import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IStatusColumn } from 'app/shared/model/status-column.model';

type EntityResponseType = HttpResponse<IStatusColumn>;
type EntityArrayResponseType = HttpResponse<IStatusColumn[]>;

@Injectable({ providedIn: 'root' })
export class StatusColumnService {
  public resourceUrl = SERVER_API_URL + 'api/status-columns';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/status-columns';

  constructor(protected http: HttpClient) {}

  create(statusColumn: IStatusColumn): Observable<EntityResponseType> {
    return this.http.post<IStatusColumn>(this.resourceUrl, statusColumn, { observe: 'response' });
  }

  update(statusColumn: IStatusColumn): Observable<EntityResponseType> {
    return this.http.put<IStatusColumn>(this.resourceUrl, statusColumn, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatusColumn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusColumn[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusColumn[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
