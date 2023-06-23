public class King extends Piece {
    public boolean canNotCastle = false;
    public King(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    @Override
    public void pieceTakes() {
        pieceKilled(pieceInteraction.findPieceInThatPosition(xPos, yPos));
    }

    @Override
    public boolean isAnyPieceOnTheWay() {
        return false;
    }

    @Override
    public boolean isMoveInPieceScope() {
        int temp1 = Math.abs(xPos - pieceInfo.getLastX());
        int temp2 = Math.abs(yPos - pieceInfo.getLastY());
        return (temp1 * temp2 == 1 || (temp1 == 0 && temp2 == 1 || temp2 == 0 && temp1 == 1)) && !kingStareDown();
    }

    public boolean kingStareDown() {
        PieceInfo temp = pieceInfo != pieceInteraction.getPieceInfos()[0][4] ? pieceInteraction.getPieceInfos()[0][4] : pieceInteraction.getPieceInfos()[2][4];
        boolean xDistance = Math.abs(xPos - temp.getLastX()) <= 1;
        boolean yDistance = Math.abs(yPos - temp.getLastY()) <= 1;
        return xDistance && yDistance;
    }

    public boolean isLegalMove() {

        return isItYourTurn() && !isAnyPieceOnTheWay() && isMoveInPieceScope() && !ifPieceDidNotMove() && !isSquareUnderAttack();
    }

    public boolean isSquareUnderAttack() {
        int tempXKing = 0;
        int tempYKing = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieceInfo.getColor() != pieceInteraction.pieceInfos[i][j].getColor()){
                    Piece piece = PieceFactory.createPiece(pieceInteraction.pieceInfos[i][j]);
                    piece.setXandY(pieceInteraction.pieceInfos[i][j].getLastX(), pieceInteraction.pieceInfos[i][j].getLastY());
                    tempYKing = pieceInfo.getLastY();
                    tempXKing = pieceInfo.getLastX();
                    pieceInfo.setPiecePosition(xPos, yPos);
                    if (piece.kingChecked()){
                        System.out.println("Figura qe sulmon : " + pieceInteraction.pieceInfos[i][j].getName());
                        pieceInfo.setPiecePosition(tempXKing, tempYKing);
                        pieceInfo.getPieceLabel().setLocation(tempXKing*100, tempYKing*100);
                        if (pieceInfo.getColor() == 'W')
                            pieceInteraction.whiteOrBlackTurn = 'B';
                        else
                            pieceInteraction.whiteOrBlackTurn = 'W';

                        return true;
                    }
                }
            }
            if (pieceInfo.getColor() == 'W')
                pieceInteraction.whiteOrBlackTurn = 'B';
            else
                pieceInteraction.whiteOrBlackTurn = 'W';
        }
        return false;
    }

}
