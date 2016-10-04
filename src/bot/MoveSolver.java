package bot;

/**
 * Created by sylvain on 16-10-03.
 */
public interface MoveSolver {

    /**
     * @param player which player to know what level is max and what level is min
     * @param level  search level of the algorithm
     * @param field: current playing field
     * @return Column where to drop the disc
     */

    int nextMove(int player, int level, Field field);



    /*
    {******************* Score ***************}
function Tform1.score(player:integer) : longint;
{Return a score for "player" for current board}

    {Local procedure ------ Count ---------}
    function count(player:integer; i,j,di,dj : longint) : longint;
    var c : longint;
    begin
      c := 0;
      while (i<nbrcols) and (j<nbrrows)and
            (i>=0) and (j>=0) and (board[i,j]=player) and (c <winnbr) do
      begin
        i := i+di;
        j := j+dj;
        inc(c);
      end;
      if c = winnbr then count := winnerval else
      count := c;
    end;

    {Local procedure --------- Sum ------------}
    function Sum(player:integer):longint;
    {count tokens in a row for this player}
    var i,j : longint;
        total : longint;
    begin  {sum}
      total := 0;
      for i := 0 to nbrcols-1 do
      for j := 0 to nbrrows-1 do
      if board[i,j] = player then
      begin
        total := total +   {Check 4 directions:}
             count(player, i,j,0,1) +
             count(player, i,j,1,0) +
             count(player, i,j,1,1) +
             count(player, i,j,1,-1);
        if total > winnerval then break;
      end;
      result := total;
    end; {sum}

var s:integer;
begin {score}
  s := sum(player mod 2 +1); {get sum for other player}
  if s  >= winnerval then result := -winnerval  {next guys wins, make score a large
                                               negative for us}
  else result := sum(player)-s;  {otherwise:  our score minus his score}
end; {score}


{************************ Minimax ******************}
function tform1.Minimax(player, searchlevel:integer;
                        {$IFDEF DEBUG} Node:TTreeNode; {$ENDIF}
                        lastmove:TPoint):integer;
{Evaluates the payoff for player Player.  Returns the payoff and
      and sets the column in global field "remember" for level 1 caller}

var
  value, temp:integer;
  i:integer;
  {$IFDEF DEBUG} newnode:TTreeNode; {$ENDIF}
  c,r:integer;
  first:boolean;
  win:boolean;
begin
  application.ProcessMessages;
  if (tag=1) or (not thinking)  then begin result:=0; exit; end; {user wants to stop}

  win:=FourInARow(lastmove.x, lastmove.y);
  if (searchlevel>=lookahead) or win or (movecount=totmoves){boardfull}
     or (runtime>maxsecs) {run time exceeded}
  then
  begin {compute the payoff of this leaf}
    if searchlevel=1 then remember:=lastmove.x;
    if win then result:=-(winnerval-searchlevel){last move was a win for the
                                                 other guy, we get a large negative}
    else result:=-score(player)+searchlevel;
    {$IFDEF DEBUG} debug.treeview1.items.addchild(node,'Last Level:'
                    +inttostr(searchlevel) +' Score: '+inttostr(result));
    {$ENDIF}
  end
  else
  begin
    first:=true;  {first time through switch}
    value:=winnerval;
    for i := 0 to nbrcols-1 do
    begin
      c:=trycols[i];    {maybe trying columns from center toward edges
                          rather than left to right}
      if (openrows[c] >=0) and (tag=0) then {there is an open row in this column
                                             and stop flag is not set}
      begin
        {make a trial move to evaluate its value}
        r:=openrows[c];
        board[c,r] := player;
        dec(openrows[c]); {one less row now available in this column}

        {$IFDEF DEBUG} newnode:=debug.treeview1.items.addchild(node,'Level:'
                       +inttostr(searchlevel)+', Col:'+inttostr(c+1));
        {$ENDIF}
        //begin
        {Knuth's "Negmax" minimax variation changes sign at each level}
        temp:= -Minimax(player mod 2+1, searchlevel+1,
                     {$IFDEF DEBUG} newnode, {$ENDIF} point(c,r));
        if first then
        begin
          value:=temp;
          first:=false;
          {$IFDEF DEBUG} newnode.text:=newnode.text+ ', Score:'+inttostr(temp)
                              + ', New MinMax:'+inttostr(value);
          {$ENDIF}
          if searchlevel=1 then remember:=c;
          (*
          application.processmessages;
           if tag=1  then begin break{result:=value; exit;} end; {user wants to stop}
          *)
        end
        else
        if value<temp then
        begin
          value:=temp;
          if searchlevel=1 then remember:=c;
          {$IFDEF DEBUG}newnode.text:=newnode.text+ ', Score:'+inttostr(temp)
                                 + ', MinMax:'+inttostr(value);
          {$ENDIF}
        end;
        {$IFDEF DEBUG}else newnode.text:=newnode.text+ ', Score:'+inttostr(temp)
                               + ', MinMax: No change';;
        {$ENDIF}
        //end;

        inc(openrows[c]); {and mark the row as available again}
        board[c,openrows[c]] := 0; {retract the move}
      end;
    end;
    result:=value;
  end;
end;


{************************ MinimaxAB **************}
function tform1.MinimaxAB(player, searchlevel:integer; alpha, beta:integer;
                        {$IFDEF DEBUG} Node:TTreeNode; {$ENDIF}
                        lastmove:TPoint):integer;
{Evaluates the payoff for "Player" using alpha-beta pruning. Returns the payoff and
 and sets the column in global field "remember" for level 1 caller}

var
  temp:integer;
  i:integer;
  {$IFDEF DEBUG} newnode:TTreeNode; {$ENDIF}
  c,r:integer;
  first:boolean;
  win:boolean;
begin
  application.processmessages; {check for stop flag}
  if tag=1 then begin result:=0; exit; end; {user wants to stop}
  win:=FourInARow(lastmove.x, lastmove.y);   {winning position?}
  if (searchlevel>=lookahead) or win or (movecount=totmoves){boardfull}
  or (runtime>maxsecs) {max run time exceeded}
  then
  begin {compute the payoff of this leaf}
    if searchlevel=1 then remember:=lastmove.x;
    if win then result:=-(winnerval-searchlevel) {win for other guy, so a loss for us}
    else result:=-(score(player)-searchlevel); {reduce scores by level so
                                  for example, immediate win give higher score
                                  than a loss on the next move}
    {$IFDEF DEBUG} debug.treeview1.items.addchild(node,'Leaf:'
                    +inttostr(searchlevel) +' Score: '+inttostr(result));
    {$ENDIF}
  end
  else
  begin
    first:=true;  {first time through switch}
    for i := 0 to nbrcols-1 do
    begin
      c:=trycols[i]; {may be trying columns from center toward edges
                      rather than left to right}
      {change to c:=i to search columns in order left to right}
      if (openrows[c] >=0) and (tag=0) then {there is an open row in this column
                                             and stop flag is not set}
      begin
        {make a trial move to evaluate its value}
        r:=openrows[c];
        board[c,r] := player;
        dec(openrows[c]); {one less row now available in this column}

        {$IFDEF DEBUG} newnode:=debug.treeview1.items.addchild(node,'Level:'
                       +inttostr(searchlevel)+', Col:'+inttostr(c+1));
        {$ENDIF}

        {Knuth's "Negmax" minimax variation changes sign at each level, alpha
         and beta swap roles at each level}
        temp:= -MinimaxAB(player mod 2+1, searchlevel+1, -beta, -alpha,
                   {$IFDEF DEBUG} newnode, {$ENDIF} point(c,r));
        if first then
        begin
          alpha:=temp;
          first:=false;
          {$IFDEF DEBUG} newnode.text:=newnode.text+ ', Score:'+inttostr(temp)
                              + ', New MinMax:'+inttostr(alpha);
          {$ENDIF}
          if searchlevel=1 then remember:=c;
        end
        else
        if temp>alpha then
        begin
          alpha:=temp;
          if searchlevel=1 then remember:=c;
          {$IFDEF DEBUG}newnode.text:=newnode.text+ ', Score:'+inttostr(temp)
                                 + ', MinMax:'+inttostr(alpha);
          {$ENDIF}
        end
        {$IFDEF DEBUG}else newnode.text:=newnode.text+ ', Score:'+inttostr(temp)
                               + ', MinMax: No change';
        {$ELSE} ;
        {$ENDIF}
        {Retract the move}
        inc(openrows[c]); {and mark the row as available again}
        board[c,openrows[c]] := 0;

        if alpha>=beta then
        begin
          {$IFDEF DEBUG} newnode.text:=newnode.text+ ', Pruned: A='+
                         inttostr(alpha) + ', B='+inttostr(beta);
          {$ENDIF}
          break;
        end;
      end;
    end;
    result:=alpha;
  end;
end;

function  TForm1.MaxRandomMoves:integer;
{Return the number of random moves that the current player will make at the
 start of a game - more random moves for dumber players}
 {Implement to keep computer games from being exactly repeatable}
 begin
   result:=maxmoves[currentplayer];
 end;

{***************** ComputerMove ***********}
procedure tform1.computermove;
{Program finds a move and does it}
var  halfc:integer;
begin
  movelbl.caption:='Thinking...'+inttostr(runtime);
  movelbl.caption:='Thinking... 0';
  halfc:=chipwidth div 2;
  thinking:=true;
  suggestmove;
  if (tag=0) and thinking then
  begin
    movelbl.caption:='Moving...';
    movelbl.update;
    Tokenmousedown(image1, mbLeft, [], halfc,0); {simulate mousedown}
    tokenMouseup(image1,mbleft,[],halfC,0); {drop token}
    application.processmessages;
  end;
  thinking:=false;
end;

{***************** SuggestMove *************}
procedure TForm1.suggestmove;
{Use minimax procedure to get a suggested next move.
 Called by SuggestBtnClick and by Computermove procedures}
var
  i,j,m:integer;
  {$IFDEF DEBUG}node:TTreenode;{$ENDIF}
begin
  with opponentgrp do if itemindex<0 then itemindex:=0;
  setlength(openrows,nbrcols);
  if gameover then initialize;
  tag:=0;  {reset stopflag}
  {$IFDEF DEBUG}
  with debug do treeview1.items.clear;
  {$ENDIF}
  screen.cursor:=crhourglass;
  drawchip(chipwidth div 2); {reset the token image}
  for i:=0 to nbrcols-1 do
  begin
    openrows[i]:=-1;
    for j:=nbrrows-1 downto 0 do if board[i,j]=0 then
    begin
      openrows[i]:=j;
      break;
    end;
  end;
  {$IFDEF DEBUG} node:=debug.Treeview1.items.add(nil,'Game Tree Root'); {$ENDIF}

  lookahead:=lookaheads[currentplayer];
  m:=maxrandomMoves;
  if m<2 then m:=2;
  if (movecount +1) div nbrplayers < m then
  begin
    thinking:=true;
    remember:=random(nbrcols);
    {debug code}{listbox1.items.add('#'+inttostr(movecount)+', P='+inttostr(currentplayer)
         +', m='+inttostr(m)+',col='+inttostr(remember+1));  }
  end
  else
  begin
    runtime:=0;
    timer1.Enabled:=true;
    if abprunebox.checked
    then Minimaxab(currentPlayer,1,-maxint, maxint,
              {$IFDEF DEBUG} node, {$ENDIF} point(0,0))
    else Minimax(currentPlayer,1,{$IFDEF DEBUG} node, {$ENDIF} point(0,0));
    timer1.enabled:=false;
  end;
  if (tag=0) and (thinking)  then movetocol(remember);
  screen.cursor:=crdefault;
end;

     */
}
