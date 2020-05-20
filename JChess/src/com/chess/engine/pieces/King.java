package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public class King extends Piece {

	final static int[] CANDIDATE_MOVE_COORDINATE = { -9, -8, -7, -1, 1, 7, 8, 9 };

	public King(Alliance pieceAlliance, int piecePosition) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

			if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)
					|| isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
				continue;
			}

			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				final Tile candididateDestinationTile = board.getTile(candidateDestinationCoordinate);
				if (!candididateDestinationTile.isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				} else {
					final Piece pieceAtDestination = candididateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					if (this.pieceAlliance != pieceAlliance) {
						legalMoves.add(
								new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));

					}
				}
			}

		}

		return legalMoves;
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.KING.toString();
	}

	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition]
				&& (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition]
				&& (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
	}

}
