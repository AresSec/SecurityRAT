import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IOptColumn } from 'app/shared/model/opt-column.model';

type EntityResponseType = HttpResponse<IOptColumn>;
type EntityArrayResponseType = HttpResponse<IOptColumn[]>;

@Injectable({ providedIn: 'root' })
export class OptColumnService {
  public resourceUrl = SERVER_API_URL + 'api/opt-columns';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/opt-columns';

  constructor(protected http: HttpClient) {}

  create(optColumn: IOptColumn): Observable<EntityResponseType> {
    return this.http.post<IOptColumn>(this.resourceUrl, optColumn, { observe: 'response' });
  }

  update(optColumn: IOptColumn): Observable<EntityResponseType> {
    return this.http.put<IOptColumn>(this.resourceUrl, optColumn, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOptColumn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOptColumn[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOptColumn[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
